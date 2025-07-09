package com.TuniRap.catalogueManagement.service;

import com.TuniRap.catalogueManagement.model.Album;
import com.TuniRap.catalogueManagement.model.CatalogueEvent;
import com.TuniRap.catalogueManagement.model.Single;
import com.TuniRap.catalogueManagement.model.Track;
import com.TuniRap.catalogueManagement.repository.AlbumRepository;
import com.TuniRap.catalogueManagement.repository.SingleRepository;
import com.TuniRap.catalogueManagement.repository.TrackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SingleRepository singleRepository;

    @Autowired
    private TrackRepository trackRepository;

    @KafkaListener(
            topics = "catalogue-events",
            groupId = "catalogue-group-1",
            containerFactory = "mykafkaListenerContainerFactory"
    )
    public void consume(CatalogueEvent event) {
        if (event == null || event.getEventType() == null) {
            logger.error("Événement Kafka invalide");
            return;
        }

        logger.info("Événement reçu : type={}, entityId={}, message={}",
                event.getEventType(), event.getEntityId(), event.getMessage());

        try {
            switch (event.getEventType()) {
                case "ALBUM_SCRAPED":
                    handleScrapedAlbum(event);
                    break;
                case "SINGLE_SCRAPED":
                    handleScrapedSingle(event);
                    break;
                case "TRACK_SCRAPED":
                    handleScrapedTrack(event);
                    break;
                case "TOP_TRACKS_SCRAPED":
                    handleScrapedTopTracks(event);
                    break;
                default:
                    logger.warn("Type d'événement non géré : {}", event.getEventType());
            }
        } catch (Exception e) {
            logger.error("Erreur lors du traitement de l'événement : {}", e.getMessage(), e);
        }
    }

    private void handleScrapedAlbum(CatalogueEvent event) {
        String[] albumData = event.getMessage().split("\\|");
        if (albumData.length < 2) {
            logger.warn("Données insuffisantes pour albumId={} : {}", event.getEntityId(), event.getMessage());
            return;
        }

        Album album = new Album();
        album.setAlbumId(event.getEntityId());
        album.setTitle(albumData[0]);
        album.setCover(albumData[1]);
        album.setScrapped(true);
        album.setScrappedAt(LocalDateTime.now());

        Album savedAlbum = albumRepository.save(album);
        logger.info("Album inséré : {}", savedAlbum);
    }

    private void handleScrapedSingle(CatalogueEvent event) {
        String[] singleData = event.getMessage().split("\\|");
        if (singleData.length < 2) {
            logger.warn("Données insuffisantes pour singleId={} : {}", event.getEntityId(), event.getMessage());
            return;
        }

        Single single = new Single();
        single.setSingleId(event.getEntityId());
        single.setName(singleData[0]);
        single.setCover(singleData[1]);
        single.setScrapped(true);
        single.setScrappedAt(LocalDateTime.now()); // <-- Ici on passe de LocalDate.now() à LocalDateTime.now()

        Single savedSingle = singleRepository.save(single);
        logger.info("Single inséré : {}", savedSingle);
    }


    private void handleScrapedTrack(CatalogueEvent event) {
        String[] trackData = event.getMessage().split("\\|");
        if (trackData.length < 6) {
            logger.warn("Données insuffisantes pour trackId={} : {}", event.getEntityId(), event.getMessage());
            return;
        }

        Track track = new Track();
        track.setTrackId(event.getEntityId());
        track.setTitle(trackData[0]);

        try {
            track.setPopularity(Integer.parseInt(trackData[1]));
            track.setDuration(Integer.parseInt(trackData[2]));
            track.setExplicit(Boolean.parseBoolean(trackData[3]));
        } catch (NumberFormatException e) {
            logger.warn("Erreur parsing dans trackId={}, message: {}", event.getEntityId(), event.getMessage());
        }

        track.setPreviewURL(trackData[4]);
        track.setCover(trackData[5]);
        track.setScrapped(true);
        track.setScrappedAt(LocalDateTime.now());

        Track savedTrack = trackRepository.save(track);
        logger.info("Track inséré : {}", savedTrack);
    }

    private void handleScrapedTopTracks(CatalogueEvent event) {
        String[] tracksInfo = event.getMessage().split("\\|");

        for (String trackInfo : tracksInfo) {
            String[] trackFields = trackInfo.split(",");
            if (trackFields.length < 6) {
                logger.warn("Données insuffisantes pour top track : {}", trackInfo);
                continue;
            }

            Track track = new Track();
            track.setTrackId(trackFields[0]);
            track.setTitle(trackFields[1]);

            try {
                track.setPopularity(Integer.parseInt(trackFields[2]));
                track.setDuration(Integer.parseInt(trackFields[3]));
                track.setExplicit(Boolean.parseBoolean(trackFields[4]));
            } catch (NumberFormatException e) {
                logger.warn("Erreur parsing dans top trackId={}, data: {}", trackFields[0], trackInfo);
            }

            track.setCover(trackFields[5]);
            track.setScrapped(true);
            track.setScrappedAt(LocalDateTime.now());

            trackRepository.save(track);
            logger.info("Top track inséré : {}", track);
        }
    }
}

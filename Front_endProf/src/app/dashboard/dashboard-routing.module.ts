import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Dashboard1Component } from "./dashboard1/dashboard1.component";
import { Dashboard2Component } from "./dashboard2/dashboard2.component";
import { NoAuthGuard } from 'app/guards/no-auth.guard'; // Ensure the correct path
import { Dashboard3Component } from './dashboard3/dashboard3.component';
import { Dashboard4Component } from './dashboard4/dashboard4.component';

const routes: Routes = [
  {
    path: 'dashboard1',
    component: Dashboard1Component,
    canActivate: [NoAuthGuard],  // Protect for Admin users
    data: { title: 'FAN' }
  },
  {
    path: 'dashboard2',
    component: Dashboard2Component,
    canActivate: [NoAuthGuard],  // Protect for Regular users
    data: { title: 'ADMIN' }
  },
  {
    path: 'dashboard3',
    component: Dashboard3Component,
    canActivate: [NoAuthGuard],  // Protect for Regular Artist
    data: { title: 'ARTIST' }
  },
  {
    path: 'dashboard4',
    component: Dashboard4Component,
    canActivate: [NoAuthGuard],  // Protect for Regular Artist
    data: { title: 'Organizateur' }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule { }

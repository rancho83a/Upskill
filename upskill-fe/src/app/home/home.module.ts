import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeAuthenticatedComponent } from './home-authenticated/home-authenticated.component';
import { HomeNotAuthenticatedComponent } from './home-not-authenticated/home-not-authenticated.component';
import { BootstrapComponent } from './bootstrap/bootstrap.component';
import {RouterModule} from '@angular/router';
import {ProfileModule} from '../profile/profile.module';
import { MatDialogModule } from '@angular/material/dialog'; 
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list'; 
import{EmployeesComponent} from '../profile/business-owner/employees/employees.component';


const materialModules = [MatDialogModule, MatSidenavModule,MatListModule];

@NgModule({
  declarations: [HomeAuthenticatedComponent, HomeNotAuthenticatedComponent, BootstrapComponent],
  imports: [
    CommonModule,
    ProfileModule,
    ...materialModules,
    RouterModule.forRoot([
      {path:'home',component:BootstrapComponent},
      {path:'employees',component:EmployeesComponent}
    ])
  ],
  exports:[HomeAuthenticatedComponent,HomeNotAuthenticatedComponent]
})
export class HomeModule { }

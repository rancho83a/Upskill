import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EmployeeRegisterComponent } from './business-owner/employee-register/employee-register.component';
import { MatInputModule, } from '@angular/material/input'; 
import { MatButtonModule } from '@angular/material/button'; 
import { NgxCsvParserModule } from 'ngx-csv-parser';
import { DashboardComponent } from './business-owner/dashboard/dashboard.component';
import { MatDialogModule } from '@angular/material/dialog';
import { EmployeesComponent } from './business-owner/employees/employees.component'; 
import { MatSidenavModule } from '@angular/material/sidenav';
import { DashboardEmployeeComponent } from './employee/dashboard-employee/dashboard-employee.component';
import { DashboardAdminComponent } from './admin/dashboard-admin/dashboard-admin.component';
import { ProfileAsideComponent } from './profile-aside/profile-aside.component'; 

const materialModules = [MatDialogModule,MatSidenavModule]


@NgModule({
  declarations: [
    EmployeeRegisterComponent,
    DashboardComponent,
    EmployeesComponent,
    DashboardAdminComponent,
    DashboardEmployeeComponent,
    ProfileAsideComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgxCsvParserModule,
    MatInputModule,
    MatButtonModule,
    ...materialModules,
    RouterModule.forChild([
      { path: 'employee-register', component: EmployeeRegisterComponent },
      { path: 'dashboard-company', component: DashboardComponent },
      { path: 'dashboard-employee', component: DashboardEmployeeComponent },
      { path: 'dashboard-admin', component: DashboardAdminComponent },
    ]),
  ],
  exports: [
    EmployeeRegisterComponent,
    DashboardComponent,
    EmployeesComponent,
    DashboardAdminComponent,
    DashboardEmployeeComponent,
    ProfileAsideComponent,
  ],
})
export class ProfileModule {}

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'; 
import{EmployeeRegisterComponent} from '../employee-register/employee-register.component'

@Component({
  selector: 'app-bo-profile-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.scss'],
})
export class EmployeesComponent implements OnInit {
  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {}

  openDialog() {
    let dialogRef = this.dialog.open(EmployeeRegisterComponent);
   
  }
}

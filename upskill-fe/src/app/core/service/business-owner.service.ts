import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { HttpClient} from '@angular/common/http';
import { IBusinessOwnerRegister, IEmployeeRegisterList,IEmployeeRegisterListView } from '../model';
import {environment} from '../../../environments/environment';

const REGISTER = '/auth/auth/register';
const COMPANY_NAME = '/profile/company/name';
const EMPLOYEE_CREATE = '/profile/profile/employee/create';
const EMPLOYEES_BY_COMPANY = '/profile/profile/get/employees-by-company';
@Injectable({
  providedIn: 'root',
})
export class BusinessOwnerService {
  constructor(private http: HttpClient) {}

  initRegister(
    user: IBusinessOwnerRegister
  ): Observable<IBusinessOwnerRegister> {
    return this.http.post<IBusinessOwnerRegister>(
      environment.apiUrl + REGISTER,
      user
    );
  }

  finishRegister(companyName: string): Observable<any> {
    return this.http.post<any>(
      environment.apiUrl + COMPANY_NAME,
      { companyName }
    );
  }

  employeeRegister(
    employees: IEmployeeRegisterList
  ): Observable<IEmployeeRegisterList> {
    return this.http.post<IEmployeeRegisterList>(
      environment.apiUrl + EMPLOYEE_CREATE,
      employees
    );
  }

  getAllEmployeeByCompany():Observable<IEmployeeRegisterListView>{
       return this.http.get<IEmployeeRegisterListView>(
         environment.apiUrl + EMPLOYEES_BY_COMPANY
       );
  }



}

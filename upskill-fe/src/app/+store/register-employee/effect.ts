import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Router } from '@angular/router';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { BusinessOwnerService } from '../../core/service';
import { ActionTypes,register, registerSuccess, registerFailed,getEmployees, getEmployeesSuccess,getEmployeesFailed} from './action';
import { of } from 'rxjs';
import { IEmployeeRegisterListView } from 'src/app/core/model';


@Injectable()
export class EmployeeRegisterEffect {
  constructor(
    private actions$: Actions,
    private service: BusinessOwnerService,
    private router: Router
  ) {}

  register$ = createEffect(() =>
    this.actions$.pipe(
      ofType(register),
      switchMap(({ employees }) => {
        return this.service.employeeRegister({ employees }).pipe(
          switchMap((payload) => {
            console.log('payload', payload);
            this.router.navigate(['home']);
            return [registerSuccess(payload as IEmployeeRegisterListView)];
          }),
          catchError((err) => of({ type: registerFailed, ...err }))
        );
      })
    )
  );

  getEmployeesByCompany = createEffect(() =>
    this.actions$.pipe(
      ofType(ActionTypes.getEmployees),
      switchMap(() => {
        return this.service.getAllEmployeeByCompany().pipe(
          map(({ employees }) => ({
            type: ActionTypes.getEmployeesSuccess,
            employees,
          }))
        );
      }),
      catchError((err) => of({ type: getEmployeesFailed, ...err }))
    )
  );
}

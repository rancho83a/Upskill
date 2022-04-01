import { HttpErrorResponse } from '@angular/common/http';
import { createAction, props } from '@ngrx/store';
import {IEmployeeRegisterList,IEmployeeRegisterListView} from '../../core/model'

export const namespace = '[REGISTER]';

export const ActionTypes = {
  register: '[Register]',
  registerSuccess: '[Register] Success',
  registerFailed: '[Register] Failed',

  getEmployees: '[Get Employees By Company] Loading',
  getEmployeesSuccess: '[Get Employees By Company] Success',
  getEmployeesFailed: '[Get Employees By Company] Failed',
};

export const register = createAction(
  `${namespace} ${ActionTypes.register}`,
  props<IEmployeeRegisterList>()
);
export const registerSuccess = createAction(
  `${namespace} ${ActionTypes.registerSuccess}`,
  props<IEmployeeRegisterListView>()
);
export const registerFailed = createAction(
  `${namespace} ${ActionTypes.registerFailed}`,
  props<{ error: HttpErrorResponse }>()
);

export const getEmployees = createAction(ActionTypes.getEmployees);
export const getEmployeesSuccess = createAction(
  ActionTypes.getEmployeesSuccess,
  props<IEmployeeRegisterListView>()
);
export const getEmployeesFailed = createAction(
  ActionTypes.getEmployeesFailed,
  props<{ error: HttpErrorResponse }>()
);

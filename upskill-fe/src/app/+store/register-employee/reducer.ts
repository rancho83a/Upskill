import { createReducer, on, Action } from '@ngrx/store';
import { IEmployeeRegisterList } from '../../core/model';
import * as registerAction from './action';

const initialState: IEmployeeRegisterList = {
  employees: [],
};

const registerReducer = createReducer(
  initialState,
  on(registerAction.registerSuccess, (state, users) => {
    return { employees: [...state.employees, ...users.employees] };
  }),
  on(registerAction.getEmployeesSuccess, (state, {employees}) => ({ ...state, employees: [...employees] }))   
);

export const featureKey = 'register';

export function reducer(state: any, action: Action): IEmployeeRegisterList {
  return registerReducer(state, action);
}

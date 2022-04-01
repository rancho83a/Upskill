import { IEmployeeRegisterList } from "src/app/core/model";
import { ILoginState } from "./login";

export interface IState {
  auth: ILoginState;
  register:IEmployeeRegisterList
}

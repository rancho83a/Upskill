import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICourseCreate } from 'src/app/course/interfaces/ICourseCreate';
import { ICourseEdit } from 'src/app/course/interfaces/ICourseEdit';
import ICourseItem from 'src/app/course/interfaces/ICourseItem';
import { environment } from 'src/environments/environment';
import { ICourseDetails } from 'src/app/course/interfaces/ICourseDetails';
import IStreamCourse from 'src/app/course/interfaces/IStreamCourse';


const ADMIN_ALL_COURSES = "/courses/courses";
const ADMIN_CREATE_COURSE = "/courses/courses/create";
const ADMIN_DELETE_COURSE = "/courses/courses/delete/";
const ADMIN_UPDATE_COURSE = "/courses/courses/edit/";
const BUSINESS_OWNER_SEARCH_COURSE = "/courses/courses/search";
const BUSINESS_OWNER_COURSES = "/courses/courses/company/catalog";
const BUSINESS_OWNER_ADD_COURSE = "/courses/courses/add/";
const BUSINESS_OWNER_REMOVE_COURSE = "/courses/courses/remove/"
const COURSE_DETAILS = "/courses/courses/details/";
const COURSE_STREAM = "/courses/courses/lectures/"



@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient) { }

  getAllAdminCourse() {

    return this.http.get<Array<ICourseItem>>(environment.apiUrl + ADMIN_ALL_COURSES);

  }

  createCourse(data) {

    return this.http.post<Array<ICourseCreate>>(environment.apiUrl + ADMIN_CREATE_COURSE, data);
  }

  deleteCourse(id: string) {

    return this.http.delete(environment.apiUrl + ADMIN_DELETE_COURSE + id);

  }

  getCourseById(id: string): Observable<ICourseEdit> {
    return this.http.get<ICourseEdit>(environment.apiUrl + ADMIN_UPDATE_COURSE + id);
  }

  editCourse(id, data) {

    return this.http.put<ICourseEdit>(environment.apiUrl + ADMIN_UPDATE_COURSE + id, data);
  }

  searchCourses(searchParam) {

    return this.http.post<Array<ICourseItem>>(environment.apiUrl + BUSINESS_OWNER_SEARCH_COURSE, searchParam);
  }

  getCoursesByBusinessOwner() {

    return this.http.get<Array<ICourseItem>>(environment.apiUrl + BUSINESS_OWNER_COURSES);
  }

  addCourseToBusinessOwner(id: string) {

    return this.http.post<string>(environment.apiUrl + BUSINESS_OWNER_ADD_COURSE + id, id);
  }

  removeCourseFromBusinessOwnerList(id: string) {
    return this.http.post<string>(environment.apiUrl + BUSINESS_OWNER_REMOVE_COURSE + id, id);
  }

  getCourseDetailsById(id: string): Observable<ICourseDetails> {

    return this.http.get<ICourseDetails>(environment.apiUrl + COURSE_DETAILS + id);
  }

  getStreamCoursebyId(id: string): Observable<IStreamCourse> {
    return this.http.get<IStreamCourse>(environment.apiUrl + COURSE_STREAM + id);
  }

}

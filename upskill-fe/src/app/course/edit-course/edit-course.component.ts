import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from 'src/app/core/service';
import { ICourseEdit } from '../interfaces/ICourseEdit';

@Component({
  selector: 'app-edit-course',
  templateUrl: './edit-course.component.html',
  styleUrls: ['./edit-course.component.scss']
})
export class EditCourseComponent implements OnInit {

  editCourseForm: FormGroup;
  fileName: string;
  uploadedPicture: FormControl;
  btn = true;

  courseToEdit: ICourseEdit;

  constructor(private fb: FormBuilder,
     private courseService: CourseService,
     private route: ActivatedRoute,
     private router: Router) { }

  ngOnInit(): void {

    this.route.params.subscribe(data => {
      let id = data['id'];
      
      this.courseService.getCourseById(id).subscribe((course) => {
        this.courseToEdit = course;
      });
  });

    this.editCourseForm = this.fb.group({
      'name': ['', [Validators.required, Validators.minLength(4)]],
      'price': ['', [Validators.required]],
      'description': ['', [Validators.required]],
      'videoUrl': ['', [Validators.required]],
      'imageUrl':['', [Validators.required]],
      'startDate': ['', [Validators.required]],
      'endDate': ['', [Validators.required]],
      'duration': ['', [Validators.required]],
      'lector': ['', [Validators.required]],
      'lectorDescription': ['', [Validators.required]],
      'skills': ['', [Validators.required]],
      'categories': ['', [Validators.required]],
      'languages': ['', [Validators.required]],
      'lectures': this.fb.array([])
    });
    
  }

  initLectures(){

    for (const existLecture of this.courseToEdit.lectures) {
      const lecture = this.fb.group({
        'id': [existLecture.id, [Validators.required]],
        'lectureName': [existLecture.lectureName, [Validators.required]],
        'resourceUrl': [existLecture.resourceUrl, [Validators.required]],
        'lectureDescription': [existLecture.lectureDescription, [Validators.required]],
      });
      this.lectures.push(lecture);
    }

    this.btn = false;
  }


  onPictureUpload(event){

    let picture:File = <File>event.target.files[0].name;

    if (picture) {
        this.fileName = picture.name;
    }
    this.uploadedPicture = this.fb.control(picture);
  }

  uploadPicture(){
    this.editCourseForm.addControl('imageUrl', this.uploadedPicture); 
  }

  addLecture(){

    const lecture = this.fb.group({
      'lectureName': ['', [Validators.required]],
      'resourceUrl': ['', [Validators.required]],
      'lectureDescription': ['', [Validators.required]],
    });
    
    this.lectures.push(lecture);
  }

  get lectures(): FormArray {
    return this.editCourseForm.controls['lectures'] as FormArray;
  }
  
  editCourse(){
    this.courseService.editCourse(this.route.snapshot.params['id'], this.editCourseForm.value).subscribe();
  
    this.router.navigate(['/admin/all']);
  }

  get c(): any {
    return this.editCourseForm.controls;
  }

  removeLecture(index: number) {
    this.lectures.removeAt(index);
  }

  reloadComponent() {
    let currentUrl = this.router.url;
        this.router.routeReuseStrategy.shouldReuseRoute = () => false;
        this.router.onSameUrlNavigation = 'reload';
        this.router.navigate([currentUrl]);
    }


  
}

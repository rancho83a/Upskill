import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from 'src/app/core/service';

@Component({
  selector: 'app-create-course',
  templateUrl: './create-course.component.html',
  styleUrls: ['./create-course.component.scss']
})
export class CreateCourseComponent implements OnInit {

  createCourseForm: FormGroup;
  fileName: string;
  uploadedPicture: FormControl;

  constructor(private fb: FormBuilder, private courseService: CourseService, private router: ActivatedRoute) { }

  ngOnInit(): void {
  
    this.createCourseForm = this.fb.group({
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
    this.addLecture();
  }

  onPictureUpload(event){

    let picture:File = <File>event.target.files[0].name;

    if (picture) {
        this.fileName = picture.name;
    }
    this.uploadedPicture = this.fb.control(picture);
  }

  uploadPicture(){
    this.createCourseForm.addControl('imageUrl', this.uploadedPicture); 
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
    return this.createCourseForm.controls['lectures'] as FormArray;
  }

  onCreateCourse(){
    this.courseService.createCourse(this.createCourseForm.value).subscribe(data => {
      this.createCourseForm.reset();
    });
  }

  get c(): any {
    return this.createCourseForm.controls;
  }

  removeLecture(index: number) {
    this.lectures.removeAt(index);
  }
}

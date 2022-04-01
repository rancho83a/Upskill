import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ICourseDetails } from '../interfaces/ICourseDetails';

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.scss']
})
export class CourseDetailsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: {details: any}) { }

  ngOnInit(): void {
  }
}

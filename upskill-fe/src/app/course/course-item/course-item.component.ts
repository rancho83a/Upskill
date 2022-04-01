import { Component, Input, OnInit, Output } from '@angular/core';
import ICourseItem from '../interfaces/ICourseItem';
import {CourseService} from '../../core/service';
import { DialogElementsComponent } from './dialog-elements/dialog-elements.component';
import { MatDialog } from '@angular/material/dialog';
import { CourseDetailsComponent } from '../course-details/course-details.component';
import { ICourseDetails} from 'src/app/course/interfaces/ICourseDetails';
import { Router } from '@angular/router';

@Component({
  selector: 'app-course-item',
  templateUrl: './course-item.component.html',
  styleUrls: ['./course-item.component.scss']
})
export class CourseItemComponent implements OnInit {

  @Input('course')
  course: ICourseItem;

  courses: Array<ICourseItem> = [];

  @Output('courseDetails')
  courseDetails:ICourseDetails;

  constructor(private courseService: CourseService, private dialogService: DialogElementsComponent, private router: Router, private dialog: MatDialog) {}

  ngOnInit(): void {
  }

  onAddCourse(id: string){
   this.courseService.addCourseToBusinessOwner(id).subscribe();
   this.reloadComponent()
  }

  onRemoveCourse(id: string){
  this.courseService.removeCourseFromBusinessOwnerList(id).subscribe();
  this.reloadComponent();
}

  onDeleteCourse(id: string){

    this.dialogService.openDialog().afterClosed().subscribe((response) =>
     {
        if(response){
      this.courseService.deleteCourse(id).subscribe(course => {
         this.reloadComponent();
      });
        }
    });
  }

reloadComponent() {
  let currentUrl = this.router.url;
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
      this.router.onSameUrlNavigation = 'reload';
      this.router.navigate([currentUrl]);
  }

onDetailsDialog(){
  console.log('clicked  on course details ')
 this.courseService.getCourseDetailsById(this.course.id)
.subscribe((data) => {
this.courseDetails = data;
console.log('course:',this.courseDetails);
});

  let dialogRef = this.dialog.open(CourseDetailsComponent, { panelClass: 'custom-mat-dialog-container',
    data: {details: {
        name: this.courseDetails.name,
       lector: this.courseDetails.lector,
       description: this.courseDetails.description,
       skills: this.courseDetails.skills.replace('\s+', '').split(','),
       duration: this.courseDetails.duration,
       lecturesCount: this.courseDetails.lecturesCount,
  }}
  });
}
}

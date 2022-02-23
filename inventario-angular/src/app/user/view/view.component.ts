import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from 'src/app/models/role';
import { UserDB } from 'src/app/models/userdb';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  public user:UserDB = new UserDB(-1, "", Role.administrator, false);
  public buttonSave = true;
  public buttonEdit = false;
  public buttonDelete = false;
  public inputID = true;
  public input = true

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    var id = Number(this.route.snapshot.paramMap.get('id'));
    if(id && id !== -1) {
      this.userService.getUserById(id).subscribe(
        result => {
          console.log(result);
          this.user = result;
        },
        error => console.error(error)
      );
    } else {
      this.buttonSave = false;
      this.buttonEdit = true;
      this.buttonDelete = true;
      this.inputID = true;
      this.input = false;
    }
  }

  save() {
    if (this.user.id === -1) {
      this.userService.addUser(this.user).subscribe(
        result => {
          console.log(result);
          this.router.navigate([""]);
          alert("se creo exitosamente");
        },
        error => console.error(error)
      );
    } else {
      this.userService.editUser(this.user).subscribe(
        result => {
          console.log(result);
          this.router.navigate([""]);
          alert("se edito exitosamente");
        },
        error => console.error(error)
      );
    } 
  }

  edit() {
    this.buttonSave = !this.buttonSave;
    this.buttonDelete = !this.buttonDelete;
    this.input = !this.input;
  }

  delete() {
    this.userService.deleteUser(this.user.id).subscribe(
      result => {
        console.log(result);
        this.router.navigate([""]);
        alert("se elimino exitosamente");
      },
      error => this.router.navigate([""])
    );
  }
}

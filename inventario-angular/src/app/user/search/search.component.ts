import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  public name:string = "";
  
  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  search() {
    //valiaciones
    if(this.name === ""){
      this.userService.getListUser().subscribe(
        result => {
          console.log(result);
          localStorage.setItem('listUsers', JSON.stringify(result));
          this.router.navigate(["/list"]);
        },
        error => alert("no hay usuarios")
      );
    } else {
      this.userService.findByName(this.name).subscribe(
        result => {
          console.log(result);
          localStorage.setItem('listUsers', JSON.stringify(result));
          this.router.navigate(["/list"]);
        },
        error => alert("no hay usuarios")
      );
    }
  }

  clean() {
    this.name = "";
  }

  add() {
    this.router.navigate(["/view/" + -1]);
  }
}

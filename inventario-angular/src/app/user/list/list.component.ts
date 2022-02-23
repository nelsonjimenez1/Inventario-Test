import { Component, OnInit } from '@angular/core';
import { UserDB} from 'src/app/models/userdb';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
  public listUsers:UserDB[] = [];
  
  constructor(private router: Router) { }

  ngOnInit(): void {
    let list = localStorage.getItem('listUsers');
    if (list) {
      this.listUsers = JSON.parse(list);
    }
  }

  nav(id:number) {
    this.router.navigate(["/view/" + id]);
  }

  add() {
    this.router.navigate(["/view/" + -1]);
  }
}

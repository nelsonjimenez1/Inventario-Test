import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'inventario-angular';

  constructor(private router: Router) {}
  ngOnInit(): void {
    if (localStorage.getItem('user')){
      this.router.navigate(["/admin/"]);
    }
    else
    {
      this.router.navigate(["/login/"]);
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UserDB } from 'src/app/models/userdb';
import { Role } from 'src/app/models/role';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public user:UserDB = new UserDB(-1, "", "", "", 0, Role.AdministratorSupport, new Date());

  constructor(private router: Router, private auth: AuthService) { }

  ngOnInit(): void { 
  }

  ngOnDestroy(): void {

  }

  doLogin() { 
    //validaciones
    this.auth.login(this.user).subscribe(
      result => {
        console.log(result);
        localStorage.setItem('user', this.user.user);
        this.router.navigate(['/admin']);
      },
      error => console.error(error)
    );
  }
}

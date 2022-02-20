import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UserDB } from 'src/app/models/userdb';
import { Role } from 'src/app/models/role';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  public user:UserDB = new UserDB(-1, "", "", "", 0, Role.AdministratorSupport, new Date());
  
  constructor(private router: Router, private auth: AuthService) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {

  }

  signUp() {
    //validaciones
    console.log(this.user);
    this.auth.signUp(this.user).subscribe(
      result => {
        console.log(result);
        this.router.navigate(['/login']);
      },
      error => console.error(error)
    );
  }
}

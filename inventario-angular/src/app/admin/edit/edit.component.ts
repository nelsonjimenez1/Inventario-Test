import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserDB } from 'src/app/models/userdb';
import { Role } from 'src/app/models/role';


@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  public product:Product=new Product(-1, "", 0, new Date(), new UserDB(-1, "", "", "", 0, Role.AdministratorSupport, new Date()));

  constructor(private productService: ProductService, private auth: AuthService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    var id = Number(this.route.snapshot.paramMap.get('id'));
    if(!id) {
      id = -1;
    }
    this.productService.getProductById(id).subscribe(
      result => {
        console.log(result);
        this.product = result;
      },
      error => console.error(error)
    );
  }

  edit() {
    //validaciones
    var user = localStorage.getItem('user');
    if(user){
      this.auth.getUserByUser(user).subscribe(
        result => {
          this.product.userDB = result;
          this.product.admissionDate = new Date();
          this.productService.editProduct(this.product).subscribe(
            result2 => {
              console.log(result2);
              alert("se edito exitosamente");
            },
            error2 => console.error(error2)
          );

        },
        error => console.error(error)
      );
    }
  }
}

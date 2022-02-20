import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { UserDB } from 'src/app/models/userdb';
import { Role } from 'src/app/models/role';
import { ProductService } from 'src/app/services/product.service';
import { AuthService } from 'src/app/services/auth.service';
@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  public product:Product=new Product(-1, "", 0, new Date(), new UserDB(-1, "", "", "", 0, Role.AdministratorSupport, new Date()));
  
  constructor(private productService: ProductService, private auth: AuthService) { }

  ngOnInit(): void {
  }

  add() {
    //valiaciones
    var user = localStorage.getItem('user');
    if(user){
      this.auth.getUserByUser(user).subscribe(
        result => {
          console.log(result);
          this.product.userDB = result;
          this.productService.addProduct(this.product).subscribe(
            result2 => {
              console.log(result2);
              alert("se agrego exitosamente");
            },
            error2 => console.error(error2)
          );

        },
        error => console.error(error)
      );
    }
    
  }
}

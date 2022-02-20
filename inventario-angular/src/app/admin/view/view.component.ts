import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { Role } from 'src/app/models/role';
import { UserDB } from 'src/app/models/userdb';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  public product:Product=new Product(-1, "", 0, new Date(), new UserDB(-1, "", "", "", 0, Role.AdministratorSupport, new Date()));

  constructor(private productService: ProductService, private route: ActivatedRoute, private router: Router) { }

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

  nav(id:number) {
    this.router.navigate(["/admin/edit/" + id]);
  }

  delete(product:Product) {
    var user = localStorage.getItem('user');
    if (product.userDB.user === user) {
      let id = product.id;
      this.productService.deleteProduct(id).subscribe(
        result => {
          console.log(result);
          this.router.navigate(["/admin/"]);
          alert("se elimino exitosamente");
        },
        error => this.router.navigate(["/admin/"])
      );
    } else  {
      alert("no puede eliminar este producto");
    }
  }
}

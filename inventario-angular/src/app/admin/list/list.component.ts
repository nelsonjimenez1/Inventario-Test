import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { ProductService } from 'src/app/services/product.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
  public listProducts:Product[] = []
  public name = "";

  constructor(private productService: ProductService, private router: Router) { }

  ngOnInit(): void {
    this.productService.getListProducts().subscribe(
      results => {
        console.log(results);
        this.listProducts = results;
      },
      error => console.error(error)
    );
  }

  nav(id:number) {
    this.router.navigate(["/admin/view/" + id]);
  }

  logout() {
    localStorage.removeItem('user');
    this.router.navigate(["/login/"]);
  }

  findByName() {
    this.productService.findByName(this.name).subscribe(
      results => {
        console.log(results);
        this.router.navigate(["/admin/view/" + results.id]);
        
      },
      error => console.error(error)
    );
  }
}

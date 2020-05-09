import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html'
})
export class NavBarComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  showAdminMovies() {
    this.router.navigate(["admin","movies"])
  }

  showLogin() {
    this.router.navigate(["login"])
  }

  showRegister() {
    this.router.navigate(["register"])
  }

  showFavorite() {
    this.router.navigate(["favorite"])
  }
}

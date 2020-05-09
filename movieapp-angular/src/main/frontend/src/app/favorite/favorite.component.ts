import {Component, OnInit} from '@angular/core';
import {MovieService} from "../service/movie.service";
import {Movie} from "../model/Movie";
import {TokenStorageService} from "../service/token-storage.service";

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html'
})
export class FavoriteComponent implements OnInit {

  userId: number
  favs: Movie []
  nonFavs: Movie []
  selectedTitle: string

  constructor(private service: MovieService, private storageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.userId = this.storageService.getUser().id
    this.getFav()
    this.getNonFav()
  }

  getFav() {
    this.service.getFav(this.userId).subscribe((data: Movie[]) => {
      this.favs = data;
    })
  }

  getNonFav() {
    this.service.getNonFav(this.userId).subscribe((data: Movie[]) => {
      this.nonFavs = data;
    })
  }

  async addFav(movie: Movie) {

    const index = this.nonFavs.indexOf(movie, 0)

    await this.service.addToFave(this.userId, movie).toPromise().then(
      data => {
        let movie: Movie = this.nonFavs[index]
        this.favs.unshift(movie)
        this.nonFavs.splice(index, 1)
      }
    ).catch(error => {
      console.log(error)
    })
  }

  async removeFav(event) {

    event.preventDefault();

    let movieId: number = +event.dataTransfer.getData("movieId")
    const index = this.favs.findIndex(item => item.id === movieId)

    await this.service.removeFav(this.userId, movieId).toPromise().then(
      res => {
        let movie: Movie = this.favs[index]
        this.nonFavs.unshift(movie)
        this.favs.splice(index, 1)
      }
    ).catch(error => {
      console.log(error)
    })
  }

  onDragStart(event: DragEvent, movie: Movie) {
    event.dataTransfer.setData("movieId", movie.id + "");
  }

  allowDrop(ev) {
    ev.preventDefault();
  }

  highlightRow(movie: Movie) {
    this.selectedTitle = movie.title
  }

}

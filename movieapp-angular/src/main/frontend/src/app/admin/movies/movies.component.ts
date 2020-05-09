import {Component, OnInit} from '@angular/core';
import {Movie} from "../../model/Movie";
import {MovieService} from "../../service/movie.service";

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html'
})
export class MoviesComponent implements OnInit  {

  newMovie: Movie
  selectedTitle:string
  movies: Movie []

  constructor(private service: MovieService) {

  }

  ngOnInit() {
    this.newMovie = new Movie()
    this.service.getMovies().subscribe((data: Movie[])=>{
      this.movies = data;
    })
  }

  highlightRow(movie) {
    this.selectedTitle = movie.title
  }

  reset() {
    this.selectedTitle = null
  }

  async deleteMovie(movie) {
    const index = this.movies.indexOf(movie, 0)
    if (index > -1) {
      await this.service.deleteMovie(movie).toPromise().then(
        res => {
          console.log(res)
          this.movies.splice(index, 1)
        }
      ).catch(error => {
        console.log(error)
      })
    }
  }

  async addMovie() {
     await this.service.addMovie(this.newMovie).toPromise().then(
       res => {
         let movie = res as Movie
         this.movies.push(movie)
       }
     ).catch(error => {
       console.log(error)
     }).finally(() => {
       this.newMovie = new Movie()
     })
  }

  async updateMovie(updatedMovie: Movie) {
    await this.service.updateMovie(updatedMovie).toPromise().then(
      res => {
        let movie = res as Movie
        console.log(movie)
      }
    ).catch(error => {
      console.log(error)
    }).finally(() => {

    })
  }

  onKey(event, movie) {
    let field = event.target.name
    let value = event.target.value
    let updatedMovie = new Movie()
    updatedMovie.id = movie.id
    updatedMovie[field] = value
    this.updateMovie(updatedMovie)
  }
}

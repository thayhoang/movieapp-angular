import { Injectable } from '@angular/core';
import {Movie} from "../model/Movie";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

const BASE_API = `${environment.url}/api`

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  url:string = "http://localhost:8080/api/movies"
  url2:string = "http://localhost:8080/api/user"

  constructor(private http: HttpClient) {

  }

  getMovies() {
    return this.http.get<Movie[]>(`${BASE_API}/movies`)
  }

  addMovie(movie: Movie) {
    return this.http.post(`${BASE_API}/movies`, movie)
  }

  updateMovie(movie: Movie) {
    return this.http.put(`${BASE_API}/movies`, movie)
  }

  deleteMovie(movie: Movie) {
    return this.http.delete(`${BASE_API}/movies`+ "/" + movie.id)
  }

  addToFave(userId: number, movie: Movie) {
    return this.http.post(`${BASE_API}/user` + "/" + userId + "/fav/" + movie.id, {})
  }

  removeFav(userId: number, movieId: number) {
    return this.http.delete(`${BASE_API}/user` + "/" + userId + "/fav/" + movieId)
  }

  getFav(userId: number) {
    return this.http.get<Movie[]>(`${BASE_API}/user` + "/"+userId+"/fav")
  }

  getNonFav(userId: number) {
    return this.http.get<Movie[]>(`${BASE_API}/user` +"/"+userId+"/nonfav")
  }
}

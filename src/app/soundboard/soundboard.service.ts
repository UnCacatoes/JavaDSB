import { Injectable } from '@angular/core';
import 'rxjs/Rx';
import { Http, Response } from '@angular/http';

@Injectable()
export class SoundboardService {

  constructor(private http: Http) { }

  getSounds (){
    return this.http.get('http://localhost:8000/api/songs').map(res => res.json());
  }

  playSound(sound){
    return this.http.get('http://localhost:8000/api/play/' + sound);
  }
}

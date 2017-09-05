import { Component, OnInit } from '@angular/core';
import { SoundboardService } from './soundboard.service';

import {FileUploader} from 'ng2-file-upload';

@Component({
  selector: 'app-soundboard',
  templateUrl: './soundboard.component.html',
  styleUrls: ['./soundboard.component.css'],
  providers: [SoundboardService]
})
export class SoundboardComponent implements OnInit {

  private sounds;
  public uploader:FileUploader = new FileUploader({url: "http://localhost:8000/api/songs"});

  constructor(private service : SoundboardService) { }

  ngOnInit() {
    this.uploader.onCompleteItem = (item: any, response: any, status: any, headers:any)=>  {
        this.getSounds();
    };
    this.getSounds();
  }

  getSounds(){
    this.service.getSounds().subscribe((sounds) => {
      this.sounds = sounds;
    });
  }

  playSound(sound){
    this.service.playSound(sound).subscribe(res => console.log(res.text()));
  }

  refresh(){
    
  }
}

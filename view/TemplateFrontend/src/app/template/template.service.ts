// template.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TemplateService {
  private apiUrl = 'http://localhost:8080/api'

  constructor(private http: HttpClient) {}

  uploadTemplate(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    return this.http.post(`${this.apiUrl}/upload`, formData);
  }

  getUploadedData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/data`);
  }

  downloadTemplate(): Observable<Blob> {
    // Adjust the headers based on your API requirementss
    const headers = new HttpHeaders({
      'Content-Type': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    });

    return this.http.get(`${this.apiUrl}/download`, { responseType: 'blob', headers });
  }

  
}

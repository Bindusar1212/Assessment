// template.component.ts
import { Component, OnInit } from '@angular/core';
import { TemplateService } from './template.service';

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.css'],
})
export class TemplateComponent implements OnInit {
  uploadedData: any[] = [];
  fileToUpload: File | null = null;

  constructor(private templateService: TemplateService) {}

  ngOnInit(): void {
    // this.refreshTable();
  }

  handleFileInput(files: FileList | null): void {
    // Use the non-null assertion operator
    this.fileToUpload = files!.item(0);
  }
  

  uploadTemplate(): void {
    if (this.fileToUpload) {
      this.templateService.uploadTemplate(this.fileToUpload).subscribe(() => {
        this.refreshTable();
      });
    }
  }

  downloadTemplate(): void {
    this.templateService.downloadTemplate().subscribe((data) => {
      // Assuming data is a Blob
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      const url = window.URL.createObjectURL(blob);

      // Create a link element and click it to trigger the download
      const a = document.createElement('a');
      a.href = url;
      a.download = 'template.xlsx';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);
    });
  }

  refreshTable(): void {
    this.templateService.getUploadedData().subscribe((data) => {
      this.uploadedData = data;
    });
  }
}

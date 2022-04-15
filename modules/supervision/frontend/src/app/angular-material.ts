// Core UI
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
// User interaction
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatSliderModule } from '@angular/material/slider';
// Rendering
import { MatBadgeModule } from '@angular/material/badge';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatRippleModule } from '@angular/material/core'; 
// Visualization
import { MatListModule } from '@angular/material/list'; 
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';


export function getAngularMaterialModules(): Array<any> {

    return [
        MatSidenavModule,
        MatToolbarModule,
        MatMenuModule,
        MatButtonModule,
        MatSlideToggleModule,
        MatSelectModule,
        MatSliderModule,
        MatBadgeModule,
        MatIconModule,
        MatProgressBarModule,
        MatProgressSpinnerModule,
        MatTooltipModule,
        MatRippleModule,
        MatListModule,
        MatTableModule,
        MatPaginatorModule,
        MatCardModule,
        MatSnackBarModule,
    ]
}
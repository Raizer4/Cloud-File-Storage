import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule]
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email, Validators.minLength(4), Validators.maxLength(20)]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(20), Validators.pattern("^[a-zA-Z0-9._-]+$")]]
    });
  }

  onBlur(field: string) {
    this.registerForm.get(field)!.markAsTouched();
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const registerData = {
        email: this.registerForm.get('email')?.value,
        password: this.registerForm.get('password')?.value,
      };
      
      this.authService.register(registerData.email, registerData.password)
        .subscribe({
          next: () => {
            console.log("User is registered correctly");
            this.router.navigateByUrl('/login');
          },
          error: (err) => {
            console.error('Register error', err);
            // Обработка ошибки (например, показ сообщения пользователю)
          }
        });
    }
  }
}
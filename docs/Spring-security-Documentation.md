Spring Security - Library Management System
Purpose

Spring Security is used to:

Authenticate users (Login/Logout)
Authorize users based on roles
Secure URLs and pages
Encrypt passwords using BCrypt
Hide unauthorized buttons and actions
Implementation Steps
1. Add Dependency
   <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
   </dependency>

Provides authentication, authorization, password encryption, and security filters.

2. Create User Entity
   User
   {
   id;
   email;
   password;
   role;
   }

Stores login credentials and user roles in the database.

3. Create UserRepository
   Optional<User> findByEmail(String email);

Used to fetch users from the database during login.

4. Create CustomUserDetailsService
   @Service
   public class CustomUserDetailsService
   implements UserDetailsService

Responsibilities:

Load user from database
Verify user exists
Provide role information to Spring Security

Flow:

Login → UserDetailsService → Database → User Found
5. Configure Password Encoder
   @Bean
   public PasswordEncoder passwordEncoder() {
   return new BCryptPasswordEncoder();
   }

Encrypts passwords before storing them in the database.

6. Configure Authentication Provider
   @Bean
   public DaoAuthenticationProvider authenticationProvider()

Connects:

Spring Security
↓
UserDetailsService
↓
Database
↓
Password Encoder
7. Create SecurityConfig
   @Configuration
   public class SecurityConfig

Main configuration class where all security rules are defined.

8. Configure URL Authorization
   Public Access
   .requestMatchers(
   "/",
   "/login",
   "/books",
   "/css/**",
   "/js/**"
   ).permitAll()

Accessible without login.

ADMIN + LIBRARIAN
.requestMatchers(
"/books/search",
"/students",
"/issues/**",
"/returns/**",
"/overdue/**"
)
.hasAnyRole("ADMIN","LIBRARIAN")

Accessible by Admin and Librarian.

ADMIN Only
.requestMatchers(
"/books/new",
"/books/save_books",
"/books/edit/**",
"/books/save_edit/**",
"/books/delete/**",
"/students/delete/**"
)
.hasRole("ADMIN")

Only Admin can add, edit, or delete records.

9. Configure Login
   .formLogin(form -> form
   .loginPage("/login")
   .usernameParameter("email")
   .passwordParameter("password")
   .defaultSuccessUrl("/", true)
   )

Uses custom login page and redirects to Home after successful login.

10. Configure Logout
    .logout(logout -> logout
    .logoutSuccessUrl("/")
    )

Logs out the user and redirects to Home page.

11. Configure Access Denied Page
    .exceptionHandling(ex -> ex
    .accessDeniedPage("/access-denied")
    )

Shows a custom page when a user lacks permission.

12. Secure Thymeleaf Pages
    Admin Only Button
<div sec:authorize="hasRole('ADMIN')">
    <a th:href="@{/books/new}">
        Add Book
    </a>
</div>
Admin + Librarian
<div sec:authorize="hasAnyRole('ADMIN','LIBRARIAN')">
    Quantity
</div>
Final Role Structure
ADMIN
View Books
Search Books
Add/Edit/Delete Books
Manage Students
Issue Books
Return Books
View Overdue Books
LIBRARIAN
View Books
Search Books
Manage Issue/Return Records
View Overdue Books

Cannot:

Add Books
Edit Books
Delete Books
GUEST
View Home Page
View Book Catalog

Cannot:

Search Books
View Students
Issue/Return Books
Access Admin Features
Complete Flow
User Login
↓
Spring Security
↓
CustomUserDetailsService
↓
UserRepository
↓
Database
↓
Password Verification (BCrypt)
↓
Role Loaded
↓
Authentication Success
↓
SecurityFilterChain Checks URL
↓
Role Authorized?
↓
YES → Access Granted
NO  → Access Denied

Result: Secure Library Management System with Authentication, Authorization, Role-Based Access Control, Password Encryption, Protected URLs, and UI-Level Security.

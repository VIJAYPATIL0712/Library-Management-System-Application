Spring Security Implementation - Library Management System
1. Why Spring Security?

Spring Security is used to:

Authenticate users (Login/Logout)
Authorize users based on roles
Protect URLs from unauthorized access
Encrypt passwords using BCrypt
Hide unauthorized actions from the UI

Example:

ADMIN can Add/Edit/Delete Books
LIBRARIAN can Issue/Return Books
Guest users can only view the book catalog
2. Add Dependency
   pom.xml
   <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   Why?

This dependency provides:

Login authentication
Authorization
Password encryption
Security filters
3. Create User Entity
   Purpose

Store login details in the database.

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String role;

}
Why?

Spring Security needs:

Username/Email
Password
Role

to identify users.

4. Create User Repository
   @Repository
   public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByEmail(String email);

}
Why?

Used to fetch users from the database during login.

5. Create CustomUserDetailsService
   @Service
   public class CustomUserDetailsService
   implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String email)
   throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()
                        )
                )
        );
   }
   }
   Why?

Spring Security calls this class during login.

Flow:

Login Form
↓
Spring Security
↓
CustomUserDetailsService
↓
Database
↓
User Found
↓
Authentication Success
6. Password Encoder
   @Bean
   public PasswordEncoder passwordEncoder() {
   return new BCryptPasswordEncoder();
   }
   Why?

Passwords should never be stored as plain text.

Example:

Password:
admin123

Stored as:

$2a$10$H8x9.....

This is safer.

7. DaoAuthenticationProvider
   @Bean
   public DaoAuthenticationProvider authenticationProvider() {

   DaoAuthenticationProvider auth =
   new DaoAuthenticationProvider();

   auth.setUserDetailsService(userDetailsService);
   auth.setPasswordEncoder(passwordEncoder());

   return auth;
   }
   Why?

Connects:

Spring Security
↓
UserDetailsService
↓
Database
↓
PasswordEncoder
8. Security Configuration
   @Configuration
   public class SecurityConfig {
   }
   Purpose

Central place where all security rules are defined.

9. Security Filter Chain
   @Bean
   public SecurityFilterChain securityFilterChain(
   HttpSecurity http) throws Exception {
   }
   Why?

Controls:

Who can access what
Login page
Logout
Roles
10. Public Pages
    .requestMatchers(
    "/",
    "/login",
    "/books",
    "/css/**",
    "/js/**"
    ).permitAll()
    Meaning

Anyone can access:

Home Page
Login Page
Books List
CSS Files
JS Files

No login required.

11. Librarian + Admin Access
    .requestMatchers(
    "/books/search",
    "/students",
    "/issues/**",
    "/returns/**",
    "/overdue/**"
    )
    .hasAnyRole("ADMIN","LIBRARIAN")
    Meaning

Only:

ADMIN
LIBRARIAN

can access these pages.

12. Admin Only Access
    .requestMatchers(
    "/books/new",
    "/books/save_books",
    "/books/edit/**",
    "/books/save_edit/**",
    "/books/delete/**"
    )
    .hasRole("ADMIN")
    Meaning

Only ADMIN can:

Add books
Edit books
Delete books

LIBRARIAN gets Access Denied.

13. Login Configuration
    .formLogin(form -> form
    .loginPage("/login")
    .usernameParameter("email")
    .passwordParameter("password")
    .defaultSuccessUrl("/", true)
    )
    Why?

Custom login page.

Form fields:

<input name="email">
<input name="password">

must match:

.usernameParameter("email")
.passwordParameter("password")
14. Logout Configuration
    .logout(logout -> logout
    .logoutSuccessUrl("/")
    )
    Why?

After logout:

User Session Destroyed
↓
Redirect Home Page
15. Access Denied Page
    .exceptionHandling(ex -> ex
    .accessDeniedPage("/access-denied")
    )
    Why?

Instead of showing:

403 Forbidden

show:

You don't have permission.
16. Hide Buttons in Thymeleaf
    Admin Only
<div sec:authorize="hasRole('ADMIN')">
    <a th:href="@{/books/new}">
        Add Book
    </a>
</div>
Why?

Librarian will never see the button.

17. Show Quantity Only To Logged Users
<div sec:authorize="hasAnyRole('ADMIN','LIBRARIAN')">
    <span th:text="${book.quantity}"></span>
</div>
Why?

Guest users can see books but not stock quantity.

18. Complete Login Flow
    User enters Email + Password
    ↓
    Spring Security
    ↓
    CustomUserDetailsService
    ↓
    UserRepository
    ↓
    Database
    ↓
    User Found
    ↓
    BCrypt Password Match
    ↓
    Role Loaded
    ↓
    Authentication Success
    ↓
    User Redirected To Home Page
19. Complete Authorization Flow
    User Requests URL
    ↓
    Security Filter Chain
    ↓
    Check User Role
    ↓
    Role Allowed?
    ↓
    Yes → Open Page
    No  → Access Denied
    Roles Used
    ADMIN

Permissions:

View Books
Search Books
Add Books
Edit Books
Delete Books
Manage Students
Issue Books
Return Books
View Overdue Books
LIBRARIAN

Permissions:

View Books
Search Books
Manage Issue Records
Manage Return Records
View Overdue Books

Cannot:

Add Books
Edit Books
Delete Books
Delete Students
GUEST

Permissions:

View Home Page
View Book Catalog

Cannot:

Search Books
View Students
Issue Books
Return Books
Access Admin Features

This documentation reflects the Spring Security structure you've implemented in your Library Management System.
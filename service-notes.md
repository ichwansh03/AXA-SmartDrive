**Entity:**
1. Penggunaan named query di entity apakah sesuai best practice?
   => this is a good thing, karena memisahkan domain class dari persistence db dan menangani semua boilerplate code yg dibutuhkan JPA

2. Bagaimana dengan anotasi column jika pada app properties sudah menggunakan SQLServerDialect? atau driverClassName atau format yg mengubah huruf kapital menjadi underscore?
   =>berfungsi untuk menyelaraskan Hibernate dengan SQL Server, memastikan bahwa kueri yang dihasilkan oleh Hibernate sesuai dengan sintaks dan perilaku yang diharapkan oleh SQL Server.

3. Apakah harus mengatur penggunaan cascade type?
   => https://www.baeldung.com/jpa-cascade-types

4. Jika terdapat composite id, apa strategy yg digunakan?
   => spesifikasinya disini https://thorben-janssen.com/composite-primary-keys-sequence/, gunakan generation type = TABLE agar uniqueness

5. Bagaimana dengan penggunaan anotasi @LastModifiedDate?
   => tambahkan anotasi @EntityListeners(AuditingEntityListener.class) pada class yg fieldnya menggunakan createdDate atau lastModifiedDate

6. Seperti apa cara kerja fetch type?
   =>https://www.baeldung.com/hibernate-lazy-eager-loading fetch type lazy akan diload ketika entity benar" dipanggil serta akan mempercepat proses load data, sementara eager tidak akan terjadi delay-initialization tapi berpengaruh pada performance query

7. Bagaimana jika terdapat nilai default?
   => Gunakan anotasi @Builder.Default pada level field

**DTO:**
1. Apakah harus menggunakan anotasi @NoArgsConstructor?
   => Harus terutama pada class response yang diimplementasikan pada ModelMapper, karena ketika di mapping harus terdapat non-private no-argument constructor, alternatif nya gunakan class record

**Repository:**
1. JpaSpesification itu apa?
   => fitur spesification hanya ada di JPA, jadi harus diextend. Implementasinya menggunakan toPredicate dari Spesification dengan parameter root, criteria query dan criteria builder yg dapat diconfig secara dinamis. Atau bisa juga gunakan fitur Query by Example, cukup masukkan contoh datanya lalu masukan example ke JPA nya jika datanya bersifat statis.
   -Example: https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Example.html
   -Spesification: https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaSpecificationExecutor.html

**Service**
1. Apakah anotasi transaction berjalan?
   => Untuk mengaktifkan anotasi transactional, tambahkan anotasi @EnableTransactionManagement jika terdapat class configuration dan memerlukan operasi transactional ke database.

* Second Level Cache: Jika data sudah banyak, implementasikan cache untuk menyimpan data query sebelumnya ketika findByxxx untuk mempercepat proses pencarian data alih-alih melakukan query langsung ke database. https://www.youtube.com/watch?v=G6Y5wDF6h5Q

* @Transactional : anotasi yang merepresentasikan rollback pada Spring https://codete.com/blog/5-common-spring-transactional-pitfalls

* Ilustrasikan operasi transactional pada java dan SQL
```java
private void test(){
   EntityManager entityManager = null;
   entityManager.createNamedQuery("asasa");
   EntityTransaction transaction = entityManager.getTransaction();
  
   try {
       transaction.begin();
      
       //to do
      
       transaction.commit();
   } catch (RuntimeException e) {
       transaction.rollback();
       throw e;
   }
}
```

* Kenapa harus menggunakan anotasi @Transactional pada setiap method?
Anotasi tersebut digunakan untuk rollback semua operasi jika terjadi kesalahan pada salah satu method. Namun anotasi transactional hanya dapat digunakan pada method yang di define pada bean lain dan memiliki modifier public.


**Unit test:**
MockMVC: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html#perform(org.springframework.test.web.servlet.RequestBuilder)

MockBean: https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/mock/mockito/MockBean.html

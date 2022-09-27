package app.easyinvoice.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import app.easyinvoice.data.auth.AuthRepository
import app.easyinvoice.data.auth.AuthRepositoryImpl
import app.easyinvoice.data.home.*
import com.google.firebase.firestore.FirebaseFirestore

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseDb(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideMyBusinessRepository(impl: MyBusinessRepositoryImpl): MyBusinessRepository = impl

    @Provides
    fun provideCustomersRepository(impl: CustomersRepositoryImpl): CustomersRepository = impl

    @Provides
    fun provideTaxRepository(impl: TaxRepositoryImpl): TaxRepository = impl

    @Provides
    fun provideInvoiceRepository(impl: InvoiceRepositoryImpl): InvoiceRepository = impl

    @Provides
    fun provideDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository = impl
}
import "./App.css";
import { BookCheckoutPage } from "./layouts/BookCheckoutPage/BookCheckoutPage";
import { HomePage } from "./layouts/HomePage/HomePage";
import { Footer } from "./layouts/NavbarAndFooter/Footer";
import { Navbar } from "./layouts/NavbarAndFooter/Navbar";
import { SearchBooksPage } from "./layouts/SearchBooksPage/SearchBooksPage";
import { ReviewListPage } from "./layouts/BookCheckoutPage/ReviewListPage/ReviewListPage";
import { ShelfPage } from "./layouts/ShelfPage/ShelfPage";
import { MessagesPage } from "./layouts/MessagesPage/MessagesPage";
import { ManageLibraryPage } from "./layouts/ManageLibraryPage/ManageLibraryPage";
import { Navigate, Route, Routes } from "react-router-dom";
import { KeycloakLogin } from "./lib/KeycloakLogin";
import { PaymentPage } from "./layouts/PaymentPage/PaymentPage";

export const App = () => {
  return (
    <div className="d-flex flex-column min-vh-100">
      <Navbar />
      <div className="flex-grow-1">
        <Routes>
          <Route path="/" element={<Navigate to="/home" replace />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/search" element={<SearchBooksPage />} />
          <Route path="/reviewlist/:bookId" element={<ReviewListPage />} />
          <Route path="/checkout/:bookId" element={<BookCheckoutPage />} />
          <Route path="/login" element={<KeycloakLogin />} />

          {/* ProtectedRoutes */}
          <Route path="/shelf" element={<ShelfPage />} />

          <Route path="/messages" element={<MessagesPage />} />

          <Route path="/admin" element={<ManageLibraryPage />} />

          <Route path="/fees" element={<PaymentPage/>} />

        </Routes>
      </div>
      <Footer />
    </div>
  );
};

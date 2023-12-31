package vn.edu.iuh.frontend.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.iuh.backend.models.Product;
import vn.edu.iuh.backend.repositories.ProductRepository;
import vn.edu.iuh.backend.services.ProductServices;
import vn.edu.iuh.frontend.models.CustomerModel;
import vn.edu.iuh.frontend.models.EmployeeModel;
import vn.edu.iuh.frontend.models.ProductModel;


import java.io.IOException;
import java.util.List;

@WebServlet("/controls")
public class ServletController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Object actionObject = req.getParameter("action");
            if (actionObject != null) {
                String action = actionObject.toString();
                if (action.equals("insertEmp")) {
                    EmployeeModel empModel = new EmployeeModel();
                    empModel.insertEmp(req, resp);
                } else if (action.equals("insertCust")) {
                    CustomerModel customerModel = new CustomerModel();
                    customerModel.insertCust(req, resp);
                } else if (action.equals("insert_products")) {
                    ProductModel pm = new ProductModel();
                    pm.insertProduct(req, resp);
                }

            } else {
                resp.sendRedirect("customerListing.jsp");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Object actionObject = req.getParameter("action");
            ProductRepository productRepository = new ProductRepository();
            if (actionObject != null) {
                String action = actionObject.toString();
                if (action.equals("cust_list")) {
                    resp.sendRedirect("customerListing.jsp");
                } else if (action.equals("delete_product")) {
                    ProductModel pm = new ProductModel();
                    pm.deleteProduct(req, resp);
                }else if(action.equals("edit_product")){
                    long id =Long.parseLong(req.getParameter("id"));
                    List<Product> listproduct =productRepository.getProdcut(id);
                    req.getSession().setAttribute("listproduct",listproduct);
                    RequestDispatcher rd = req.getRequestDispatcher("updateProduct.jsp");
                    rd.include(req,resp);
                } else if (action.equals("update_product")){
                    ProductModel pm = new ProductModel();
                    pm.updateProduct(req, resp);
                }
            } else {
                resp.sendRedirect("test.jsp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

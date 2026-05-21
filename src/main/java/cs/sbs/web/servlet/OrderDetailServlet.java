package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/order/*")
public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String path = request.getPathInfo();
        if (path == null || path.length() <= 1) {
            out.println("Error: Order not found");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(path.substring(1));
        } catch (NumberFormatException e) {
            out.println("Error: Order not found");
            return;
        }

        Map<Integer, Order> orderDB = (Map<Integer, Order>) getServletContext().getAttribute("orderDB");
        if (orderDB == null) {
            out.println("Error: Order not found");
            return;
        }

        Order order = orderDB.get(orderId);
        if (order == null) {
            out.println("Error: Order not found");
        } else {
            out.println("Order Details");
            out.println("Order ID: " + order.getOrderId());
            out.println("Customer: " + order.getCustomerName());
            out.println("Food: " + order.getFoodName());
            out.println("Quantity: " + order.getQuantity());
        }
    }
}
package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/order/create")
public class OrderCreateServlet extends HttpServlet {
    @Override
    public void init() {
        // 把订单库存到全局上下文，让OrderDetailServlet读取
        ServletContext ctx = getServletContext();
        if (ctx.getAttribute("orderDB") == null) {
            ctx.setAttribute("orderDB", new HashMap<Integer, Order>());
            ctx.setAttribute("autoId", 1);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");

        String customer = request.getParameter("customer");
        String food = request.getParameter("food");
        String qtyStr = request.getParameter("quantity");

        if (customer == null || food == null || qtyStr == null || customer.isBlank() || food.isBlank()) {
            out.println("Error: Missing required parameters");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyStr);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            out.println("Error: Invalid quantity");
            return;
        }

        ServletContext ctx = getServletContext();
        Map<Integer, Order> orderDB = (Map<Integer, Order>) ctx.getAttribute("orderDB");
        int autoId = (Integer) ctx.getAttribute("autoId");

        Order newOrder = new Order(autoId, customer, food, quantity);
        orderDB.put(autoId, newOrder);
        out.println("Order Created: " + autoId);

        ctx.setAttribute("autoId", autoId + 1);
    }
}
package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
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
    private final Map<Integer, Order> orderDB = new HashMap<>();
    private int autoId = 1;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");

        // 获取表单参数
        String customer = request.getParameter("customer");
        String food = request.getParameter("food");
        String qtyStr = request.getParameter("quantity");

        // 参数校验
        if (customer == null || food == null || qtyStr == null || customer.isEmpty() || food.isEmpty()) {
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

        // 创建订单
        Order newOrder = new Order(autoId, customer, food, quantity);
        orderDB.put(autoId, newOrder);
        out.println("Order Created: " + autoId);
        autoId++;
    }

    // 给订单详情Servlet共享订单数据
    public Map<Integer, Order> getOrderDB() {
        return orderDB;
    }
}
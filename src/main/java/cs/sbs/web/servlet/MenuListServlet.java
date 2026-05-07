package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/menu")
public class MenuListServlet extends HttpServlet {
    private List<MenuItem> menuList;

    @Override
    public void init() {
        // 初始化菜品数据
        menuList = new ArrayList<>();
        menuList.add(new MenuItem(1, "Rice Bowl", 18));
        menuList.add(new MenuItem(2, "Noodles", 16));
        menuList.add(new MenuItem(3, "Burger", 22));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 获取Query参数 name（模糊搜索）
        String searchName = request.getParameter("name");

        out.println("Menu List:");
        for (MenuItem item : menuList) {
            if (searchName == null || searchName.isEmpty() || item.getName().toLowerCase().contains(searchName.toLowerCase())) {
                out.printf("%d. %s - %.0f%n", item.getId(), item.getName(), item.getPrice());
            }
        }
    }
}
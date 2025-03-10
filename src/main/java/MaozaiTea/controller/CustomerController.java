package MaozaiTea.controller;

import MaozaiTea.pojo.Customer;
import MaozaiTea.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/login")
    public String customerLogin(){
        return "index";
    }

    @RequestMapping(value = "/show/{page}")
    public String showCustomer(Model model, @PathVariable("page") int page, Customer customer) {
        List<Customer> customerList1 = customerService.getCustomerByOr(customer);
        List<Customer> customerList = new ArrayList<Customer>();
        int customerListLength = customerList1.size();
        int pageCount = customerListLength / 5;
        if ((customerListLength % 5) != 0) ++pageCount;
        if (pageCount < 1) pageCount = 1;
        if (page > pageCount) page = pageCount;
        if (page < 1) page = 1;
        for (int i = (page-1)*5, cnt = 0; i < customerListLength && cnt < 5; ++i, ++cnt) {
            customerList.add(customerList1.get(i));
        }
        model.addAttribute("customers", customerList);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("curPage", page);
        model.addAttribute("customerName", customer.getCustomerName());
        model.addAttribute("customerSex", customer.getCustomerSex());
        return "showCustomer";
    }
    @RequestMapping(value = "/add")
    public String addCustomer() {
        return "addCustomer";
    }

    @RequestMapping(value = "/addCustomer")
    public String addCustomer(Model model, Customer customer) {
        customerService.addCustomer(customer);

        System.out.println(customer);

        List<Customer> customerList = customerService.getAllCustomer();
        int customerListLength = customerList.size();
        int pageCount = customerListLength / 5;
        if ((customerListLength % 5) != 0) ++pageCount;
        if (pageCount < 1) pageCount = 1;

        model.addAttribute("curPage", 1);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("customerSex", null);
        model.addAttribute("customerName", null);
        model.addAttribute("customers", customerList.subList(0, min(5, customerListLength)));

        return "showCustomer";
    }

    @RequestMapping(value = "/queryCustomer")
    public String queryCustomer(Model model, Customer customer) {
        if (customer.getCustomerSex().equals("选择性别")) customer.setCustomerSex(null);

        List<Customer> customerList = customerService.getCustomerByOr(customer);
        int customerListLength = customerList.size();
        int pageCount = customerListLength / 5;
        if ((customerListLength % 5) != 0) ++pageCount;
        if (pageCount < 1) pageCount = 1;

        model.addAttribute("customers", customerList.subList(0, min(5, customerListLength)));
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("curPage", 1);
        model.addAttribute("customerName", customer.getCustomerName());
        model.addAttribute("customerSex", customer.getCustomerSex());
        return "showCustomer";
    }

    @RequestMapping(value = "/deleteCustomer/{page}/{customerID}")
    public String deleteCustomer(Model model, @PathVariable("customerID") int customerID, @PathVariable("page") int page, Customer customer, String customerName, String customerSex) {
        customerService.deleteCustomer(customerID);

        List<Customer> customerList1 = customerService.getCustomerByOr(customer);
        List<Customer> customerList = new ArrayList<Customer>();
        int customerListLength = customerList1.size();
        int pageCount = customerListLength / 5;
        if ((customerListLength % 5) != 0) ++pageCount;
        if (pageCount < 1) pageCount = 1;
        if (page > pageCount) page = pageCount;
        if (page < 1) page = 1;
        for (int i = (page-1)*5, cnt = 0; i < customerListLength && cnt < 5; ++i, ++cnt) {
            customerList.add(customerList1.get(i));
        }
        model.addAttribute("customers", customerList);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("curPage", page);
        model.addAttribute("customerName", customerName);
        model.addAttribute("customerSex", customerSex);

        return "showCustomer";
    }

    //    ----------------update by 任黎明---------------------
    @RequestMapping(value = "/update/{page}/{customerID}")
    public String getCustomerById(@PathVariable("customerID") int customerID, @PathVariable("page") int page, Model model, String customerName, String customerSex){
            Customer customer = customerService.getCustomerByID(customerID);
            model.addAttribute("customer",customer);
            model.addAttribute("curPage", page);
            model.addAttribute("customerQueryName", customerName);
            model.addAttribute("customerQuerySex", customerSex);
            return "updateCustomer";
    }
    @RequestMapping(value="/saveUpdate/{page}/{customerID}")
    public String updateCustomer(@PathVariable("customerID") int customerID, @PathVariable("page") int page, Model model, String customerQueryName, String customerQuerySex,
                                 Customer customer) {
        customerService.updateCustomer(customer);

        Customer customer1 = new Customer();
        customer1.setCustomerName(customerQueryName);
        customer1.setCustomerSex(customerQuerySex);
        List<Customer> customerList1 = customerService.getCustomerByOr(customer1);
        List<Customer> customerList = new ArrayList<Customer>();
        int customerListLength = customerList1.size();
        int pageCount = customerListLength / 5;
        if ((customerListLength % 5) != 0) ++pageCount;
        if (pageCount < 1) pageCount = 1;
        if (page > pageCount) page = pageCount;
        if (page < 1) page = 1;
        for (int i = (page-1)*5, cnt = 0; i < customerListLength && cnt < 5; ++i, ++cnt) {
            customerList.add(customerList1.get(i));
        }
        model.addAttribute("customers", customerList);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("curPage", page);
        model.addAttribute("customerName", customerQueryName);
        model.addAttribute("customerSex", customerQuerySex);

        return "showCustomer";
    }
    //--------------------------------------------------------

}

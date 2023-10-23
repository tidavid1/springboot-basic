package com.programmers.springbootbasic.common.handler;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.utils.ConsoleIOManager;
import com.programmers.springbootbasic.domain.customer.controller.CustomerController;
import com.programmers.springbootbasic.domain.voucher.controller.VoucherController;
import com.programmers.springbootbasic.domain.wallet.controller.WalletController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommandHandler {
    private final ConsoleIOManager consoleIOManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    public void run() {
        CommandType command;
        consoleIOManager.println(CommandOutput.PROGRAM_SELECT.getValue());
        try {
            command = CommandType.from(consoleIOManager.getInput());
            switch (command) {
                case VOUCHER -> voucherProgram();
                case CUSTOMER -> customerProgram();
                case EXIT -> consoleIOManager.printSystemMsg(CommandOutput.EXIT.getValue());
                default -> consoleIOManager.printSystemMsg(CommandOutput.WRONG_CHOICE.getValue());
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    private void voucherProgram() {
        CommandType command = CommandType.INIT;
        do {
            consoleIOManager.println(CommandOutput.VOUCHER_MENU.getValue());
            try {
                command = CommandType.from(consoleIOManager.getInput());
                switch (command) {
                    case CREATE -> createVoucher();
                    case LIST -> listVoucher();
                    case FIND -> findVoucher();
                    case UPDATE -> updateVoucher();
                    case DELETE -> deleteVoucher();
                    case DELETE_ALL -> deleteAllVouchers();
                    case CUSTOMER -> showWalletsByVoucher();
                    case EXIT -> consoleIOManager.printSystemMsg(CommandOutput.EXIT.getValue());
                    default -> consoleIOManager.printSystemMsg(CommandOutput.WRONG_CHOICE.getValue());
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        } while (!command.equals(CommandType.EXIT));
    }

    private void customerProgram() {
        CommandType command = CommandType.INIT;
        do {
            consoleIOManager.println(CommandOutput.CUSTOMER_MENU.getValue());
            try {
                command = CommandType.from(consoleIOManager.getInput());
                switch (command) {
                    case CREATE -> createCustomer();
                    case LIST -> listCustomers();
                    case BLACKLIST -> blacklist();
                    case ADD_BLACKLIST -> addBlacklist();
                    case REMOVE_BLACKLIST -> removeBlacklist();
                    case DELETE_ALL -> deleteAllCustomers();
                    case ADD_VOUCHER -> addVoucherInWallet();
                    case REMOVE_VOUCHER -> removeVoucherFromWallet();
                    case WALLET -> showWalletByCustomer();
                    case EXIT -> consoleIOManager.printSystemMsg(CommandOutput.EXIT.getValue());
                    default -> consoleIOManager.printSystemMsg(CommandOutput.WRONG_CHOICE.getValue());
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        } while (!command.equals(CommandType.EXIT));
    }

    private void createVoucher() throws IOException {
        consoleIOManager.println(CommandOutput.VOUCHER_SELECT.getValue());
        String voucherType = consoleIOManager.getInput();
        consoleIOManager.println(CommandOutput.REQUEST_VOUCHER_VALUE.getValue());
        String value = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.createVoucher(voucherType, value);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void listVoucher() {
        voucherController.findAllVouchers()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void findVoucher() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.findVoucherById(voucherId);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getMessage());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getMessage());
        }
    }

    private void updateVoucher() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_VALUE.getValue());
        String value = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.updateVoucher(voucherId, value);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void deleteVoucher() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.deleteVoucher(voucherId);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void deleteAllVouchers() {
        CommonResult commonResult = voucherController.deleteAllVouchers();
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void showWalletsByVoucher() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = walletController.findWalletsByVoucherId(voucherId);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getMessage());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getMessage());
        }
    }

    private void createCustomer() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        consoleIOManager.print(CommandOutput.REQUEST_NAME.getValue());
        String name = consoleIOManager.getInput();
        CommonResult commonResult = customerController.createCustomer(email, name);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void listCustomers() {
        customerController.findAllCustomer()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void blacklist() {
        customerController.findAllBlacklistCustomer()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void addBlacklist() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        CommonResult commonResult = customerController.addCustomerInBlacklist(email);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void removeBlacklist() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        CommonResult commonResult = customerController.removeCustomerInBlacklist(email);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void deleteAllCustomers() {
        CommonResult commonResult = customerController.deleteAllCustomer();
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void addVoucherInWallet() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        listVoucher();
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = walletController.addWallet(email, voucherId);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void removeVoucherFromWallet() throws IOException {
        String email = showWalletByCustomer();
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = walletController.deleteWallet(email, voucherId);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private String showWalletByCustomer() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        CommonResult commonResult = walletController.findWalletsByCustomerEmail(email);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getMessage());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getMessage());
        }
        return email;
    }
}

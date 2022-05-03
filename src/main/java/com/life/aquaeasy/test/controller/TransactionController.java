package com.life.aquaeasy.test.controller;

import com.life.aquaeasy.test.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionRepository transactionRepository;

	@GetMapping(value = "/transaction-summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTransactionSummary(
			@RequestParam(name = "startDate", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = "endDate", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
	) {
		log.trace("StartDate: {} || EndDate: {}", startDate, endDate);

		transactionRepository.findAll()
				.forEach(transaction -> {
					log.debug(">>> {}", transaction);
				});

		var total_income = transactionRepository.findAll().stream()
				.filter(x -> this.checkCategory(x.getCategory().name()).equalsIgnoreCase("total_income"))
				.mapToDouble(it -> it.getAmount().doubleValue())
				.sum();

		var total_savings = transactionRepository.findAll().stream()
				.filter(x -> this.checkCategory(x.getCategory().name()).equalsIgnoreCase("total_savings"))
				.mapToDouble(it -> it.getAmount().doubleValue())
				.sum();

		var total_expenses = transactionRepository.findAll().stream()
				.filter(x -> this.checkCategory(x.getCategory().name()).equalsIgnoreCase("total_expenses"))
				.mapToDouble(it -> it.getAmount().doubleValue()).sum();

		var sumExpensesMap = transactionRepository.findAll().stream()
				.filter(x -> this.checkCategory(x.getCategory().name()).equalsIgnoreCase("total_expenses"))
				.collect(Collectors.groupingBy(it -> it.getCategory().name(), Collectors.summingDouble(x -> x.getAmount().doubleValue())));


		var maximumExpenses = sumExpensesMap.values().stream().min(Comparator.naturalOrder()).get();

		var maximumExpensesCategory = sumExpensesMap
				.entrySet()
				.stream()
				.filter(entry -> Objects.equals(entry.getValue(), maximumExpenses))
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());

		// TODO: I doing this way to trying avoid same maximum expense amount occurred in the future the system only show once of the category which might cause miss-leading...
		var top_expense_category = new StringBuilder();
		top_expense_category.append(maximumExpenses);
		maximumExpensesCategory.forEach(it -> top_expense_category.append("@ ").append(it));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total_income", total_income);
		jsonObject.put("total_expenses", total_expenses);
		jsonObject.put("total_savings", total_savings);
		jsonObject.put("top_expense_category", top_expense_category);

		return jsonObject.toString();
	}

	private String checkCategory(String category) {
		switch (category) {
			case "TRAVEL":
			case "ONLINE_SHOPPING":
			case "GROCERY":
			case "HOUSING_LOAN":
				return "total_expenses";
			case "STOCK_TRADING":
				return "total_savings";
			case "SALARY":
				return "total_income";
			default:
				log.error("Fail to find category");
				return "";
		}
	}

}

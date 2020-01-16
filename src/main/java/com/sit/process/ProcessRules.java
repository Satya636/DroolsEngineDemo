package com.sit.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;

import com.sit.model.Product;

public class ProcessRules {

	public static void main(String[] args) throws DroolsParserException, IOException {
		ProcessRules rules = new ProcessRules();
		rules.executeDrools();
	}

	public void executeDrools() throws DroolsParserException, IOException {
		String ruleFile = "/com/sit/rule/rules.drl";
		InputStream inputStream = getClass().getResourceAsStream(ruleFile);

		Reader reader = new InputStreamReader(inputStream);

		PackageBuilder packageBuilder = new PackageBuilder();
		packageBuilder.addPackageFromDrl(reader);

		org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();

		ruleBase.addPackage(rulesPackage);

		Product product = new Product();
		product.setType("diamond");

		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		workingMemory.insert(product);
		workingMemory.fireAllRules();

		
		System.out.println(product.getDiscount());
	}

}

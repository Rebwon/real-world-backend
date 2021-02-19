package com.rebwon.realworldbackend;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = RealWorldBackendApplication.class)
public class ArchUnitModulePackageTest {

  /*@ArchTest
  ArchRule modulesPackageRule = classes().that().resideInAPackage("com.rebwon.realworldbackend.modules..")
      .should().onlyBeAccessed().byClassesThat()
      .resideInAnyPackage("com.rebwon.realworldbackend.modules..");*/

  @ArchTest
  ArchRule cycleCheck = slices().matching("com.rebwon.realworldbackend.modules.(*)..")
      .should().beFreeOfCycles();
}

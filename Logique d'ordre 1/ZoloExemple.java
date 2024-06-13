/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.commons.syntax.Constant;
import org.tweetyproject.logics.commons.syntax.Predicate;
import org.tweetyproject.logics.commons.syntax.Sort;
import org.tweetyproject.logics.fol.parser.FolParser;
import org.tweetyproject.logics.fol.reasoner.FolReasoner;
import org.tweetyproject.logics.fol.reasoner.SimpleFolReasoner;
import org.tweetyproject.logics.fol.syntax.FolBeliefSet;
import org.tweetyproject.logics.fol.syntax.FolFormula;
import org.tweetyproject.logics.fol.syntax.FolSignature;

/**
 *
 * @author Dell
 */
public class ZoloExemple {

    public static void main(String[] args) throws ParserException, IOException {

        FolSignature sig = new FolSignature(true);

        Sort sortAnimal = new Sort("Animal");
        sig.add(sortAnimal);

        Constant constantB = new Constant("b", sortAnimal);
        Constant constantC = new Constant("c", sortAnimal);
        Constant constantA = new Constant("a", sortAnimal);

        sig.add(constantB, constantC, constantA);

        List<Sort> predicateListCeph = new ArrayList<Sort>();
        predicateListCeph.add(sortAnimal);
        Predicate cephPredicate = new Predicate("ceph", predicateListCeph);
        sig.add(cephPredicate);

        List<Sort> predicateListNaut = new ArrayList<Sort>();
        predicateListNaut.add(sortAnimal);
        Predicate nautPredicate = new Predicate("naut", predicateListNaut);
        sig.add(nautPredicate);

        List<Sort> predicateListMol = new ArrayList<Sort>();
        predicateListMol.add(sortAnimal);
        Predicate molPredicate = new Predicate("mol", predicateListMol);
        sig.add(molPredicate);

        List<Sort> predicateListAcoq = new ArrayList<Sort>();
        predicateListAcoq.add(sortAnimal);
        Predicate acoqPredicate = new Predicate("acoq", predicateListAcoq);
        sig.add(acoqPredicate);

        FolParser parser = new FolParser();
        parser.setSignature(sig); //Use the signature defined above
        FolBeliefSet bs = new FolBeliefSet();
        FolFormula f1 = (FolFormula) parser.parseFormula("forall X: (naut(X) => ceph(X))");
        FolFormula f2 = (FolFormula) parser.parseFormula("forall X: (ceph(X) => mol(X))");
        FolFormula f3 = (FolFormula) parser.parseFormula("forall X: ((mol(X) && !(ceph(X) && !naut(X))) => acoq(X))");
        FolFormula f4 = (FolFormula) parser.parseFormula("forall X: ((ceph(X) && !naut(X)) => !acoq(X))");
        FolFormula f5 = (FolFormula) parser.parseFormula("forall X: (naut(X) => acoq(X))");
        FolFormula f6 = (FolFormula) parser.parseFormula("naut(a)");
        FolFormula f7 = (FolFormula) parser.parseFormula("ceph(b)");
        FolFormula f8 = (FolFormula) parser.parseFormula("mol(c)");
        bs.add(f1, f2, f3, f4, f5, f6, f7, f8);
        System.out.println("\nParsed BeliefBase: " + bs);

        FolReasoner.setDefaultReasoner(new SimpleFolReasoner()); //Set default prover, options are NaiveProver, EProver, Prover9
        FolReasoner prover = FolReasoner.getDefaultReasoner();
        System.out.println("ANSWER 1: " + prover.query(bs, (FolFormula) parser.parseFormula("acoq(a)")));
        System.out.println("ANSWER 2: " + prover.query(bs, (FolFormula) parser.parseFormula("acoq(b)")));
        System.out.println("ANSWER 3: " + prover.query(bs, (FolFormula) parser.parseFormula("!acoq(c)")));
    }

}

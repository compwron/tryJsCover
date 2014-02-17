package com.olabini.jescov;

import java.util.*;

public class FileCoverage {
    private final String filename;
    private final Collection<com.olabini.jescov.LineCoverage> allLineCoverage;
    private final Collection<com.olabini.jescov.BranchCoverage> allBranchCoverage;
    private final Map<Integer, com.olabini.jescov.LineCoverage> lineCoverage;
    private final Map<Integer, Collection<com.olabini.jescov.BranchCoverage>> branchCoverage;

    public FileCoverage(String filename, Collection<com.olabini.jescov.LineCoverage> lineCoverage, Collection<com.olabini.jescov.BranchCoverage> branchCoverage) {
        this.filename = filename;
        this.allLineCoverage = lineCoverage;
        this.allBranchCoverage = branchCoverage;
        this.lineCoverage = generateLineCoverage();
        this.branchCoverage = generateBranchCoverage();
    }

    private Map<Integer, com.olabini.jescov.LineCoverage> generateLineCoverage() {
        Map<Integer, com.olabini.jescov.LineCoverage> result = new TreeMap<Integer, com.olabini.jescov.LineCoverage>();
        for(com.olabini.jescov.LineCoverage lc : allLineCoverage) {
            com.olabini.jescov.LineCoverage existing = result.get(lc.getLine());
            if(existing != null) {
                result.put(lc.getLine(), new com.olabini.jescov.LineCoverage(lc.getLine(), existing.getHits() + lc.getHits()));
            } else {
                result.put(lc.getLine(), lc);
            }
        }
        return result;
    }

    private Map<Integer, Collection<com.olabini.jescov.BranchCoverage>> generateBranchCoverage() {
        Map<Integer, Collection<com.olabini.jescov.BranchCoverage>> result = new TreeMap<Integer, Collection<com.olabini.jescov.BranchCoverage>>();
        for(com.olabini.jescov.BranchCoverage bc : allBranchCoverage) {
            Collection<com.olabini.jescov.BranchCoverage> lbc = result.get(bc.getLine());
            if(null == lbc) {
                lbc = new LinkedList<com.olabini.jescov.BranchCoverage>();
                result.put(bc.getLine(), lbc);
                lbc.add(bc);
            } else {
                com.olabini.jescov.BranchCoverage existing = null;
                for(com.olabini.jescov.BranchCoverage ebc : lbc) {
                    if(ebc.getBranchId() == bc.getBranchId()) {
                        existing = ebc;
                    }
                }
                if(existing == null) {
                    lbc.add(bc);
                } else {
                    lbc.remove(existing);
                    lbc.add(existing.plus(bc));
                }
            }
        }
        return result;
    }

    public Collection<com.olabini.jescov.BranchCoverage> getBranchCoverageFor(Integer line) {
        return branchCoverage.get(line);
    }

    public com.olabini.jescov.LineCoverage getLineCoverageFor(Integer line) {
        return lineCoverage.get(line);
    }

    public Collection<Integer> getLines() {
        Set<Integer> allLines = new HashSet<Integer>();
        allLines.addAll(lineCoverage.keySet());
        allLines.addAll(branchCoverage.keySet());
        return allLines;
    }

    public String getFilename() {
        return filename;
    }

    public int getLinesValid() {
        int sum = 0;
        for(com.olabini.jescov.LineCoverage lc : allLineCoverage) {
            sum += lc.getLinesValid();
        }
        return sum;
    }

    public int getBranchesValid() {
        int sum = 0;
        for(com.olabini.jescov.BranchCoverage bc : allBranchCoverage) {
            sum += bc.getBranchesValid();
        }
        return sum;
    }

    public int getLinesCovered() {
        int sum = 0;
        for(com.olabini.jescov.LineCoverage lc : allLineCoverage) {
            sum += lc.getLinesCovered();
        }
        return sum;
    }

    public int getBranchesCovered() {
        int sum = 0;
        for(com.olabini.jescov.BranchCoverage bc : allBranchCoverage) {
            sum += bc.getBranchesCovered();
        }
        return sum;
    }

    public double getLineRate() {
        return getLinesCovered() / (double)getLinesValid();
    }

    public double getBranchRate() {
        return getBranchesCovered() / (double)getBranchesValid();
    }

    public FileCoverage plus(FileCoverage other) {
        Collection<com.olabini.jescov.LineCoverage> allLines = new ArrayList<com.olabini.jescov.LineCoverage>();
        allLines.addAll(this.allLineCoverage);
        allLines.addAll(other.allLineCoverage);
        
        Collection<com.olabini.jescov.BranchCoverage> allBranches = new ArrayList<com.olabini.jescov.BranchCoverage>();
        allBranches.addAll(this.allBranchCoverage);
        allBranches.addAll(other.allBranchCoverage);
        
        return new FileCoverage(this.filename, allLines, allBranches);
    }
}

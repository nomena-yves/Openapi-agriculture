package tsutsu.exam_final.DTO;


import tsutsu.exam_final.Entity.Frequency;

import java.time.LocalDate;

public class CreateMemberShipFeeDto {

    private LocalDate eligibleFrom;
    private Frequency frequency;
    private Double amount;
    private String label;

    public CreateMemberShipFeeDto() {}

    public LocalDate getEligibleFrom() { return eligibleFrom; }
    public void setEligibleFrom(LocalDate eligibleFrom) { this.eligibleFrom = eligibleFrom; }

    public Frequency getFrequency() { return frequency; }
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}

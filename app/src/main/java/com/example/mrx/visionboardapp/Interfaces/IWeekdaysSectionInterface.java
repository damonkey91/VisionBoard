package com.example.mrx.visionboardapp.Interfaces;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;

public interface IWeekdaysSectionInterface {
    public void clickedCreateTask(int sectionNr);
    public void clickedFinishedTask(int adapterPosition, int sectionNr, Section section, int points);
}

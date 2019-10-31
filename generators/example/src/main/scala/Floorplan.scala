package example

import aoplib.floorplan.{FloorplanAspectNew}
import chisel3.{Data, Vec}
import chisel3.Bundle
import chisel3.aop.Select
import firrtl.options.{RegisteredLibrary, ShellOption}
import firrtl.{AnnotationSeq}
import barstools.floorplan._
import barstools.floorplan.firrtl.{FloorplanModuleAnnotation}

object Floorplans {

  def layoutTopNew(th: TestHarness): AnnotationSeq = {
    val top = th.dut
    val tileMacro = FloorplanModuleAnnotation(top.outer.tiles.head.module.toTarget, ConcreteMacro("tile", LengthUnit(500), LengthUnit(200)).serialize)
    Seq(FloorplanModuleAnnotation(top.outer.sbus.module.toTarget, ConcreteMacro("sbus", LengthUnit(500), LengthUnit(500)).serialize))
  }
}

case class RocketFloorplan() extends RegisteredLibrary {
  val name = "Rocket-Floorplan"
  val options = Seq(new ShellOption[String](
    longOption = "floorplan",
    toAnnotationSeq = {
      case "simple" => Seq(FloorplanAspectNew("Simple_Rocket","test_run_dir/html/myfloorplan",{ t: TestHarness => Floorplans.layoutTopNew(t) }))
    },
    helpText = "The name of a mini floorplan must be <dci|icd> indicating the relative positions of the icache, core, and dcache.",
    helpValueName = Some("<dci|icd>")))
}

# Create simulator
set ns [new Simulator]

# Trace files
set tracefile [open out.tr w]
$ns trace-all $tracefile

set namfile [open out.nam w]
$ns namtrace-all $namfile

# Nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

# Links
$ns duplex-link $n0 $n1 1Mb 1ms DropTail
$ns duplex-link $n1 $n2 1Mb 1ms DropTail

# Agents
set tcp [new Agent/TCP]
$ns attach-agent $n0 $tcp

set sink [new Agent/TCPSink]
$ns attach-agent $n2 $sink

# Connect sender to receiver
$ns connect $tcp $sink

# Application
set ftp [new Application/FTP]
$ftp attach-agent $tcp   

# Start/Stop
$ns at 0.5 "$ftp start"
$ns at 4.5 "$ftp stop"
$ns at 5.0 "finish"

# Finish procedure
proc finish {} {
    global ns namfile tracefile
    $ns flush-trace
    close $namfile
    close $tracefile
    exec nam out.nam &
    exit 0
}

# Run simulation
$ns run


# Run Commands
# ns udp.tcl
# nam out.nam
